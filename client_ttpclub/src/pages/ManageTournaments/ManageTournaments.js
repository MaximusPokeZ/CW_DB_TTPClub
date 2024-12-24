import React, { useEffect, useState } from "react";
import { axiosInstance } from "../../config/Axios/axiosConfig";
import "./ManageTournaments.css"

const ManageTournaments = () => {
    const [tournaments, setTournaments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userId, setUserId] = useState("");
    const [selectedTournament, setSelectedTournament] = useState(null);
    const [registeredPlayers, setRegisteredPlayers] = useState([]);

    useEffect(() => {
        const fetchTournaments = async () => {
            try {
                const response = await axiosInstance.get("http://localhost:8080/api/v1/tournaments");
                const tournamentsData = response.data.map(tournament => ({
                    id: tournament.eventId,
                    name: tournament.eventName,
                    prizePool: tournament.prizePool,
                    isTeamBased: tournament.isTeamBased,
                    maxRating: tournament.maxParticipantRating,
                    startTime: new Date(tournament.startTime),
                    maxParticipants: tournament.maxParticipants,
                }));
                setTournaments(tournamentsData);
            } catch (error) {
                console.error("Error when receiving tournament data:", error);
                alert("Tournament data could not be uploaded. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        const email = localStorage.getItem("userEmail");
        const fetchUserData = async () => {
            try {
                const response = await axiosInstance.get(`http://localhost:8080/api/v1/user/get_email/${email}`);
                setUserId(response.data.id);
            } catch (error) {
                console.error("Error fetching user data", error);
                alert("Couldn't upload user data");
            }
        };

        fetchUserData();
        fetchTournaments();
    }, []);

    const handleRegister = async (eventId) => {
        try {
            await axiosInstance.post("http://localhost:8080/api/v1/registration", {
                userId: userId,
                eventId: eventId,
            });
            alert("You have successfully registered for the tournament!");
        } catch (error) {
            console.error("Error during registration:", error);
            if (error.response && error.response.status === 409) {
                alert("You are already registered for this event!");
            } else {
                alert("An error occurred during registration.");
            }
        }
    };

    const handleTournamentClick = async (tournamentId, tournamentName) => {
        setSelectedTournament({ id: tournamentId, name: tournamentName });

        try {
            const response = await axiosInstance.get(
                `http://localhost:8080/api/v1/registration/event/${tournamentId}`
            );
            setRegisteredPlayers(response.data);
        } catch (error) {
            console.error("Error when fetching registered players:", error);
            alert("Error fetching registered players");
        }
    };

    const handleUnregister = async (userId, eventId) => {
        try {
            await axiosInstance.delete(`http://localhost:8080/api/v1/registration/delete/${userId}/${eventId}`);
            alert("You have successfully unregistered from the training!");

            if (selectedTournament) {
                const response = await axiosInstance.get(
                    `http://localhost:8080/api/v1/registration/event/${selectedTournament.id}`
                );
                setRegisteredPlayers(response.data);
            }
        } catch (error) {
            console.error("Error during unregistration:", error);
            alert("An error occurred during unregistration.");
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div className="tournaments-list">
            <h2>Tournaments List</h2>
            <div className="tournaments-container">
                {tournaments.length > 0 ? (
                    tournaments.map((tournament) => (
                        <div
                            className="tournament-item"
                            key={tournament.id}
                            onClick={() => handleTournamentClick(tournament.id, tournament.name)}
                        >
                            <h3 className="tournament-name">{tournament.name}</h3>
                            <div className="tournament-details">
                                <p>
                                    <strong>Prize Pool:</strong>{" "}
                                    {new Intl.NumberFormat("en-US", {
                                        style: "currency",
                                        currency: "RUB",
                                    }).format(tournament.prizePool)}
                                </p>
                                <p>
                                    <strong>Team-Based:</strong>{" "}
                                    {tournament.isTeamBased ? "Yes" : "No"}
                                </p>
                                <p>
                                    <strong>Maximum Participant Rating:</strong>{" "}
                                    {tournament.maxRating}
                                </p>
                                <p>
                                    <strong>Start Date and Time:</strong>{" "}
                                    {tournament.startTime.toLocaleString("en-US", {
                                        year: "numeric",
                                        month: "long",
                                        day: "numeric",
                                        hour: "2-digit",
                                        minute: "2-digit",
                                    })}
                                </p>
                                <p>
                                    <strong>Maximum Number of Participants:</strong>{" "}
                                    {tournament.maxParticipants}
                                </p>
                            </div>
                            <button
                                onClick={() => handleRegister(tournament.id)}
                                className="register-button"
                            >
                                Register
                            </button>
                        </div>
                    ))
                ) : (
                    <p>No tournaments available at the moment.</p>
                )}
            </div>

            {selectedTournament && (
                <div className="tournament-players">
                    <h2>Players in {selectedTournament.name}</h2>
                    {registeredPlayers.length > 0 ? (
                        <table className="players-table">
                            <thead>
                            <tr>
                                <th>Username</th>
                                <th>Rating</th>
                                <th>Age</th>
                            </tr>
                            </thead>
                            <tbody>
                            {registeredPlayers.map((player) => (
                                <tr key={player.id}>
                                    <td>{player.username}</td>
                                    <td>{player.rating}</td>
                                    <td>{player.age}</td>
                                    <td>
                                        {player.id === userId && (
                                            <button
                                                onClick={() => handleUnregister(player.id, selectedTournament.id)}
                                                className="unregister-button"
                                            >
                                                Cancel Registration
                                            </button>
                                        )}
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No players have registered for this tournament yet.</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default ManageTournaments;

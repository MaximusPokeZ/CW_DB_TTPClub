import React, { useEffect, useState } from "react";
import { axiosInstance } from "../../config/Axios/axiosConfig";
import "./ManageTournaments.css"

const ManageTournaments = () => {
    const [tournaments, setTournaments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userId, setUserId] = useState("");

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
            alert("Вы успешно зарегистрировались на турнир!");
        } catch (error) {
            console.error("Error during registration:", error);
            alert(
                `Error during registration: ${
                    error.response?.data?.message || "Unexpected error"
                }`
            );
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
                        <div className="tournament-item" key={tournament.id}>
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
        </div>
    );
};

export default ManageTournaments;

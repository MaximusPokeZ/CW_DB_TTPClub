import React, { useEffect, useState } from "react";
import { axiosInstance } from "../../config/Axios/axiosConfig";
import "./ManageTrainings.css";

const ManageTrainings = () => {
    const [trainings, setTrainings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userId, setUserId] = useState("");
    const [selectedTraining, setSelectedTraining] = useState(null);
    const [registeredPlayers, setRegisteredPlayers] = useState([]);

    useEffect(() => {
        const fetchTrainings = async () => {
            try {
                const response = await axiosInstance.get("http://localhost:8080/api/v1/trainings");
                const trainingsData = response.data.map(training => ({
                    id: training.eventId,
                    name: training.eventName,
                    startTime: new Date(training.startTime),
                    endTime: new Date(training.endTime),
                    maxParticipants: training.maxParticipants,
                    type: training.type,
                    coachName: training.coachName,
                }));
                setTrainings(trainingsData);
            } catch (error) {
                console.error("Error when receiving training data:", error);
                alert("Training data could not be uploaded. Please try again later.");
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
        fetchTrainings();
    }, []);

    const handleRegister = async (trainingId, trainingType) => {
        if (trainingType === "INDIVIDUAL") {
            const response = await axiosInstance.get(
                `http://localhost:8080/api/v1/registration/event/${trainingId}`
            );

            if (response.data.length >= 1) {
                alert(
                    "Individual training is limited to one participant only. Someone is already registered!"
                );
                return;
            }
        }
        try {
            await axiosInstance.post("http://localhost:8080/api/v1/registration", {
                userId: userId,
                eventId: trainingId,
            });
            alert("You have successfully registered for the training!");
        } catch (error) {
            console.error("Error during registration:", error);

            if (error.response && error.response.status === 409) {
                alert("You are already registered for this training!");
            } else {
                alert("An error occurred during registration.");
            }
        }
    };


    const handleTrainingClick = async (trainingId, trainingName) => {
        setSelectedTraining({ id: trainingId, name: trainingName });

        try {
            const response = await axiosInstance.get(
                `http://localhost:8080/api/v1/registration/event/${trainingId}`
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

            if (selectedTraining) {
                const response = await axiosInstance.get(
                    `http://localhost:8080/api/v1/registration/event/${selectedTraining.id}`
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
        <div className="trainings-list">
            <h2>Trainings List</h2>
            <div className="trainings-container">
                {trainings.length > 0 ? (
                    trainings.map((training) => (
                        <div
                            className="training-item"
                            key={training.id}
                            onClick={() => handleTrainingClick(training.id, training.name)}
                        >
                            <h3 className="training-name">{training.name}</h3>
                            <div className="training-details">
                                <p>
                                    <strong>Coach:</strong> {training.coachName}
                                </p>
                                <p>
                                    <strong>Type:</strong> {training.type}
                                </p>
                                <p>
                                    <strong>Start Time:</strong> {training.startTime.toLocaleString("en-US")}
                                </p>
                                <p>
                                    <strong>End Time:</strong> {training.endTime.toLocaleString("en-US")}
                                </p>
                                <p>
                                    <strong>Max Participants:</strong> {training.maxParticipants}
                                </p>
                            </div>
                            <button
                                onClick={() => handleRegister(training.id, training.type)}
                                className="register-button"
                            >
                                Register
                            </button>
                        </div>
                    ))
                ) : (
                    <p>No trainings available at the moment.</p>
                )}
            </div>
            {selectedTraining && (
                <div className="training-players">
                    <h3>Participants for {selectedTraining.name}</h3>
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
                                            onClick={() => handleUnregister(player.id, selectedTraining.id)}
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
                </div>
            )}
        </div>
    );
};

export default ManageTrainings;

import React, { useState, useEffect } from "react";
import { axiosInstance } from "../../../config/Axios/axiosConfig";
import "./ManageEvent.css";

const ManageEvent = () => {
    const [events, setEvents] = useState([]);

    // Общие данные события
    const [name, setName] = useState("");
    const [type, setEventType] = useState("");
    const [description, setDescription] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [maxParticipants, setMaxParticipants] = useState("");

    // Данные турнира
    const [prizePool, setPrizePool] = useState("");
    const [isTeamBased, setIsTeamBased] = useState(false);
    const [maxParticipantRating, setMaxParticipantRating] = useState("");

    // Данные тренировки
    const [coachId, setCoachId] = useState("");
    const [trainingType, setTrainingType] = useState("");

    // Состояние редактирования
    const [editingEventId, setEditingEventId] = useState(null);
    const [isEditing, setIsEditing] = useState(false);

    // Получение списка событий
    const fetchEvents = async () => {
        try {
            const response = await axiosInstance.get("http://localhost:8080/api/v1/events");
            setEvents(response.data);
        } catch (error) {
            console.error("Error fetching events", error);
            alert("Error loading events.");
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);

    const fetchTournamentDetails = async (eventId) => {
        try {
            const response = await axiosInstance.get(
                `http://localhost:8080/api/v1/tournaments/${eventId}`
            );
            const tournament = response.data;
            setPrizePool(tournament.prizePool);
            setIsTeamBased(tournament.isTeamBased);
            setMaxParticipantRating(tournament.maxParticipantRating);
        } catch (error) {
            console.error("Error fetching tournament details", error);
        }
    };
    // Отправка формы
    const handleSubmit = async (event) => {
        event.preventDefault();

        const baseEventData = {
            name,
            type,
            description,
            startTime,
            endTime,
            maxParticipants
        };

        try {
            if (isEditing) {
                await axiosInstance.put(
                    `http://localhost:8080/api/v1/events/update_event/${editingEventId}`,
                    baseEventData
                );

                if (type === "TOURNAMENT") {
                    await axiosInstance.put(`http://localhost:8080/api/v1/tournaments/${editingEventId}`, {
                        editingEventId,
                        prizePool,
                        isTeamBased,
                        maxParticipantRating,
                    });
                } else if (type === "TRAINING") {
                    await axiosInstance.put(`http://localhost:8080/api/v1/trainings/${editingEventId}`, {
                        editingEventId,
                        coachId,
                        trainingType,
                    });
                }

                alert("Event updated successfully!");
            } else {
                const eventResponse = await axiosInstance.post(
                    "http://localhost:8080/api/v1/events/create_event",
                    baseEventData
                );
                const eventId = eventResponse.data.id;
                if (type === "TOURNAMENT") {
                    await axiosInstance.post(`http://localhost:8080/api/v1/tournaments`, {
                        eventId,
                        prizePool,
                        isTeamBased,
                        maxParticipantRating
                    });
                } else if (type === "TRAINING") {
                    await axiosInstance.post(`http://localhost:8080/api/v1/trainings`, {
                        eventId,
                        coachId,
                        trainingType
                    });
                }

                alert("Event created successfully!");
            }

            fetchEvents();
            resetForm();
        } catch (error) {
            console.error("Error saving event", error);
            alert("Error saving event.");
        }
    };

    const handleEdit = async (event) => {
        setEditingEventId(event.id);
        setName(event.name);
        setEventType(event.type);
        setDescription(event.description);
        setStartTime(event.startTime);
        setEndTime(event.endTime);
        setMaxParticipants(event.maxParticipants);

        if (event.type === "TOURNAMENT") {
            await fetchTournamentDetails(event.id);
        } else if (event.type === "TRAINING") {
            setCoachId(event.coachId);
            setTrainingType(event.trainingType);
        }

        setIsEditing(true);
    };

    const handleDelete = async (eventId) => {
        if (window.confirm("Are you sure you want to delete this event?")) {
            try {
                await axiosInstance.delete(
                    `http://localhost:8080/api/v1/events/delete_event/${eventId}`
                );
                alert("Event deleted successfully!");
                fetchEvents();
            } catch (error) {
                console.error("Error deleting event", error);
                alert("Error deleting event.");
            }
        }
    };

    const resetForm = () => {
        setEditingEventId(null);
        setName("");
        setEventType("");
        setDescription("");
        setStartTime("");
        setEndTime("");
        setMaxParticipants("");
        setPrizePool("");
        setIsTeamBased(false);
        setMaxParticipantRating("");
        setCoachId("");
        setTrainingType("");
        setIsEditing(false);
    };

    return (
        <div className="event-management-container">
            <h2>Event Management</h2>

            <div className="form-container">
                <h3>{isEditing ? "Edit Event" : "Add New Event"}</h3>
                <form className="event-form" onSubmit={handleSubmit}>
                    {/* Общие поля */}
                    <div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="type">Event Type:</label>
                        <select
                            id="type"
                            value={type}
                            onChange={(e) => setEventType(e.target.value)}
                            required
                        >
                            <option value="">Select Type</option>
                            <option value="TOURNAMENT">Tournament</option>
                            <option value="TRAINING">Training</option>
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="description">Description:</label>
                        <textarea
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                        ></textarea>
                    </div>

                    <div className="form-group">
                        <label htmlFor="startTime">Start Time:</label>
                        <input
                            type="datetime-local"
                            id="startTime"
                            value={startTime}
                            onChange={(e) => setStartTime(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="endTime">End Time:</label>
                        <input
                            type="datetime-local"
                            id="endTime"
                            value={endTime}
                            onChange={(e) => setEndTime(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="maxParticipants">Max Participants:</label>
                        <input
                            type="number"
                            id="maxParticipants"
                            value={maxParticipants}
                            onChange={(e) => setMaxParticipants(e.target.value)}
                            min="0"
                            max="100"
                            step="1"
                            required
                        />
                    </div>

                    {/* Поля для турниров */}
                    {type === "TOURNAMENT" && (
                        <>
                            <div className="form-group">
                                <label htmlFor="prizePool">Prize Pool:</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    id="prizePool"
                                    value={prizePool}
                                    onChange={(e) => setPrizePool(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="isTeamBased">Team Based:</label>
                                <select
                                    id="isTeamBased"
                                    value={isTeamBased ? "true" : "false"}
                                    onChange={(e) =>
                                        setIsTeamBased(e.target.value === "true")
                                    }
                                    required
                                >
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label htmlFor="maxParticipantRating">
                                    Max Participant Rating:
                                </label>
                                <input
                                    type="number"
                                    id="maxParticipantRating"
                                    value={maxParticipantRating}
                                    onChange={(e) =>
                                        setMaxParticipantRating(e.target.value)
                                    }
                                    required
                                />
                            </div>
                        </>
                    )}

                    {/* Поля для тренировок */}
                    {type === "TRAINING" && (
                        <>
                            <div className="form-group">
                                <label htmlFor="coachId">Coach ID:</label>
                                <input
                                    type="number"
                                    id="coachId"
                                    value={coachId}
                                    onChange={(e) => setCoachId(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="trainingType">Training Type:</label>
                                <input
                                    type="text"
                                    id="trainingType"
                                    value={trainingType}
                                    onChange={(e) => setTrainingType(e.target.value)}
                                    required
                                />
                            </div>
                        </>
                    )}

                    <div className="form-buttons">
                        <button type="submit" className="save-button">
                            {isEditing ? "Update" : "Create"}
                        </button>
                        {isEditing && (
                            <button
                                type="button"
                                className="cancel-button"
                                onClick={resetForm}
                            >
                                Cancel
                            </button>
                        )}
                    </div>
                </form>
            </div>

            <div className="event-table">
                <h3>Event List</h3>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {events.map((event) => (
                        <tr key={event.id}>
                            <td>{event.name}</td>
                            <td>{event.type}</td>
                            <td>{new Date(event.startTime).toLocaleString()}</td>
                            <td>{new Date(event.endTime).toLocaleString()}</td>
                            <td>
                                <button
                                    onClick={() => handleEdit(event)}
                                    className="edit-button"
                                >
                                    Edit
                                </button>
                                <button
                                    onClick={() => handleDelete(event.id)}
                                    className="delete-button"
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ManageEvent;

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../config/Axios/axiosConfig"
import "./MainPage.css";

const MainPage = () => {
    const [username, setUsername] = useState("");
    const [role, setRole] = useState("");
    const [users, setUsers] = useState([]);
    const [tournaments, setTournaments] = useState([]);
    const navigate = useNavigate();

    const email = localStorage.getItem("userEmail");
    useEffect(() => {
        if (!email) {
            navigate("/", { replace: true });
            return;
        }
        const fetchUserData = async () => {
            try {
                const response = await axiosInstance.get(`http://localhost:8080/api/v1/user/get_email/${email}`);
                setUsername(response.data.username);
                setRole(response.data.role);
            } catch (error) {
                console.error("Error fetching user data", error);
                alert("Couldn't upload user data");
            }
        };


        if (email) {
            fetchUserData();
        }

        const fetchUsers = async () => {
            try {
                const response = await axiosInstance.get("http://localhost:8080/api/v1/user/sorted_by_rating");
                const userData = response.data.map(user => ({
                    username: user.username,
                    rating: user.rating
                }));
                setUsers(userData);
            } catch (error) {
                console.error("Error fetching users data", error);
                alert("Couldn't upload users data");
            }
        };

        const fetchTournaments = async () => {
            try {
                const response = await axiosInstance.get("http://localhost:8080/api/v1/tournaments");
                const tournamentsData = response.data.map(tournament => ({
                    name: tournament.eventName,
                    prizePool: tournament.prizePool,
                    isTeamBased: tournament.isTeamBased,
                    maxRating: tournament.maxParticipantRating,
                    startTime: new Date(tournament.startTime),
                    maxParticipants: tournament.maxParticipants,
                }));
                console.log("Processed tournament data:", tournamentsData);
                setTournaments(tournamentsData);
            } catch (error) {
                console.error("Error when receiving tournament data:", error);
                alert("Tournament data could not be uploaded");
                return [];
            }
        };

        fetchUsers();
        fetchTournaments();
    }, [email]);




    const handleUsernameClick = () => {
        console.log(role);
        if (role === "admin") {
            navigate(`/admin/${username}`);
        } else {
            navigate(`/profile/${username}`);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("userEmail");
        alert("You have successfully logged out");
        navigate("/");
    };

    return (
        <div className="main-page-container">
            <header className="main-header">
                <div className="header-right">
                    {username && (
                        <span className="username" onClick={handleUsernameClick}>
                    {username}
                </span>
                    )}
                    <button className="logout-button" onClick={handleLogout}>
                        Выйти
                    </button>
                </div>
            </header>

            <main className="main-content">
                <div className="tournaments-list">
                    <h2>Tournaments List</h2>
                    <div className="tournaments-container">
                        {tournaments.length > 0 ? (
                            tournaments.map((tournament, index) => (
                                <div className="tournament-item" key={index}>
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
                                            {new Date(tournament.startTime).toLocaleString("en-US", {
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
                                </div>
                            ))
                        ) : (
                            <p>Loading...</p>
                        )}
                    </div>
                </div>

                <div className="center-actions">
                    <button onClick={() => navigate("/equipment")}>Equipment reservations</button>
                    <button onClick={() => navigate("/trainings")}>Sign up for training sessions</button>
                    <button onClick={() => navigate("/tournaments")}>Sign up for tournaments</button>
                </div>

                <div className="ratings-list">
                    <h2>User Rating</h2>
                    <div className="ratings-container">
                        {users.length > 0 ? (
                            users.map((user, index) => (
                                <div className="rating-item" key={index}>
                                    <span className="rating-username">{user.username}</span>
                                    <span className="rating-value">{user.rating}</span>
                                </div>
                            ))
                        ) : (
                            <p>Loading...</p>
                        )}
                    </div>
                </div>
            </main>

            <div className="gallery-wrap">
                <h2 className="gallery-title">Gallery</h2>
                <div className="gallery">
                    <div>
                        <span><img src="/images/image-1.png" alt="Изображение 1"/></span>
                        <span><img src="/images/image-2.png" alt="Изображение 2"/></span>
                        <span><img src="/images/image-3.png" alt="Изображение 3"/></span>
                    </div>
                </div>
            </div>

            <div className="our-team">
                <h2>Our team</h2>
                <div className="team-members">
                    {[
                        {name: "Максим Заславцев", role: "Администратор"},
                        {name: "Антон Заславцев", role: "Главный тренер"},
                        {name: "Кто-то", role: "Тренер"},
                    ].map((coach, index) => (
                        <div key={index} className="team-member">
                            <div className="team-placeholder">Фото</div>
                            <h3>{coach.name}</h3>
                            <p>{coach.role}</p>
                        </div>
                    ))}
                </div>
            </div>

            <div className="club-rules">
                <h2>Club rules</h2>
                <ul>
                    <li>Respect the other participants and coaches.</li>
                    <li>Keep the room clean and tidy.</li>
                    <li>Use the equipment only for its intended purpose.</li>
                    <li>Sign up for training sessions and tournaments in advance.</li>
                    <li>Follow the safety regulations.</li>
                </ul>
            </div>

            <footer className="main-footer">
                <p>Contacts: +7 916 624 06 71 | Address: Moscow, PuPuPu str., 52</p>
            </footer>
        </div>
    );
};

export default MainPage;

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./MainPage.css";

const MainPage = () => {
    const [username, setUsername] = useState("");
    const [role, setRole] = useState("");
    const navigate = useNavigate();

    const email = localStorage.getItem("userEmail");
    useEffect(() => {
        if (!email) {
            navigate("/", { replace: true });
            return;
        }
        const fetchUserData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/v1/user/get_email/${email}`);
                setUsername(response.data.username);
                setRole(response.data.role);
            } catch (error) {
                console.error("Error fetching user data", error);
                alert("Не удалось загрузить данные пользователя.");
            }
        };

        if (email) {
            fetchUserData();
        }
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
        alert("Вы успешно вышли из системы.");
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
                    <h2>Список турниров</h2>
                    {/* Добавьте компонент или код для отображения списка турниров */}
                </div>

                <div className="center-actions">
                    <button onClick={() => navigate("/equipment")}>Бронь оборудования</button>
                    <button onClick={() => navigate("/trainings")}>Запись на тренировки</button>
                    <button onClick={() => navigate("/tournaments")}>Запись на турниры</button>
                </div>

                <div className="ratings-list">
                    <h2>Рейтинг пользователей</h2>
                    {/* Добавьте компонент или код для отображения рейтинга */}
                </div>
            </main>

            <footer className="main-footer">
                <p>Контакты: +7 916 624 06 71 | Адрес: г. Москва, ул. ПуПуПу, 52</p>
            </footer>
        </div>
    );
};

export default MainPage;

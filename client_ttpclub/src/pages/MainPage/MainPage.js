import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../config/Axios/axiosConfig"
import "./MainPage.css";

const MainPage = () => {
    const [username, setUsername] = useState("");
    const [role, setRole] = useState("");
    const [users, setUsers] = useState([]);
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
                alert("Не удалось загрузить данные пользователя.");
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
                alert("Не удалось загрузить данные пользователей.");
            }
        };

        fetchUsers();
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
                    <div className="ratings-container">
                        {users.length > 0 ? (
                            users.map((user, index) => (
                                <div className="rating-item" key={index}>
                                    <span className="rating-username">{user.username}</span>
                                    <span className="rating-value">{user.rating}</span>
                                </div>
                            ))
                        ) : (
                            <p>Загрузка...</p>
                        )}
                    </div>
                </div>
            </main>

            {/* Галерея */}
            <div className="gallery-wrap">
                <h2 className="gallery-title">Галерея</h2>
                <div className="gallery">
                    <div>
                        <span><img src="/images/image-1.png" alt="Изображение 1"/></span>
                        <span><img src="/images/image-2.png" alt="Изображение 2"/></span>
                        <span><img src="/images/image-3.png" alt="Изображение 3"/></span>
                    </div>
                </div>
            </div>

            {/* Наша команда */}
            <div className="our-team">
                <h2>Наша команда</h2>
                <div className="team-members">
                {[
                        {name: "Иван Иванов", role: "Главный тренер"},
                        {name: "Ольга Смирнова", role: "Ассистент тренера"},
                        {name: "Максим Петров", role: "Тренер"},
                    ].map((coach, index) => (
                        <div key={index} className="team-member">
                            <div className="team-placeholder">Фото</div>
                            <h3>{coach.name}</h3>
                            <p>{coach.role}</p>
                        </div>
                    ))}
                </div>
            </div>

            {/* Правила клуба */}
            <div className="club-rules">
                <h2>Правила клуба</h2>
                <ul>
                    <li>Уважайте других участников и тренеров.</li>
                    <li>Соблюдайте чистоту и порядок в помещении.</li>
                    <li>Используйте оборудование только по назначению.</li>
                    <li>Записывайтесь на тренировки и турниры заранее.</li>
                    <li>Соблюдайте правила техники безопасности.</li>
                </ul>
            </div>

            <footer className="main-footer">
                <p>Контакты: +7 916 624 06 71 | Адрес: г. Москва, ул. ПуПуПу, 52</p>
            </footer>
        </div>
    );
};

export default MainPage;

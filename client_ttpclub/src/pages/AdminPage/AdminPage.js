import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "./AdminPage.css";

const AdminPage = () => {
    const { username } = useParams();
    const [userData, setUserData] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const fetchAdminData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/v1/user/get_username/${username}`);
                setUserData(response.data);
            } catch (error) {
                console.error("Error fetching admin data", error);
                alert("Не удалось загрузить данные администратора.");
            }
        };

        fetchAdminData();
    }, [username]);

    return (
        <div className="admin-page-container">
            <h1>Страница администратора</h1>
            <div className="admin-info">
                <p><strong>Имя:</strong> {userData.username}</p>
                <p><strong>Email:</strong> {userData.email}</p>
                <p><strong>Телефон:</strong> {userData.phone}</p>
                <p><strong>Роль:</strong> {userData.role}</p>
                <p><strong>Рейтинг:</strong> {userData.rating}</p>
            </div>
            <div className="admin-actions">
                <button onClick={() => navigate(`/admin/${username}/manage-users`)}>Управление пользователями</button>
                <button onClick={() => navigate("/manage-tournaments")}>Управление событиями</button>
                <button onClick={() => navigate("/reports")}>Отчеты</button>
            </div>
        </div>
    );
};

export default AdminPage;

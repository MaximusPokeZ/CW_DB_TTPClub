import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { axiosInstance } from "../../config/Axios/axiosConfig";
import "./AdminPage.css";

const AdminPage = () => {
    const { username } = useParams();
    const [userData, setUserData] = useState({});
    const [selectedFile, setSelectedFile] = useState(null); // Состояние для выбранного файла
    const navigate = useNavigate();

    useEffect(() => {
        const fetchAdminData = async () => {
            try {
                const response = await axiosInstance.get(`http://localhost:8080/api/v1/user/get_username/${username}`);
                setUserData(response.data);
            } catch (error) {
                console.error("Error fetching admin data", error);
                alert("Не удалось загрузить данные администратора.");
            }
        };

        fetchAdminData();
    }, [username]);

    const handleDumpDatabase = async () => {
        try {
            await axiosInstance.get(`http://localhost:8080/api/v1/db/dump`);
            alert(`Дамп базы данных сохранен`);
        } catch (error) {
            console.error("Ошибка при создании дампа", error);
            alert("Ошибка при создании дампа базы данных.");
        }
    };

    const handleRestoreDatabase = async () => {
        if (!selectedFile) {
            alert("Пожалуйста, выберите файл для восстановления.");
            return;
        }

        const formData = new FormData();
        formData.append("file", selectedFile);

        try {
            await axiosInstance.post(`http://localhost:8080/api/v1/db/restore`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            alert("База данных успешно восстановлена.");
        } catch (error) {
            console.error("Ошибка при восстановлении базы данных", error);
            alert("Ошибка при восстановлении базы данных.");
        }
    };

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

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
                <button onClick={() => navigate(`/admin/${username}/manage-tournaments`)}>Управление событиями</button>
                <div>
                    <h3>Сохранить дамп базы данных</h3>
                    <button onClick={handleDumpDatabase}>Сохранить дамп</button>
                </div>
                <div>
                    <h3>Восстановить базу данных</h3>
                    <input type="file" onChange={handleFileChange} />
                    <button onClick={handleRestoreDatabase}>Восстановить дамп</button>
                </div>
            </div>
        </div>
    );
};

export default AdminPage;

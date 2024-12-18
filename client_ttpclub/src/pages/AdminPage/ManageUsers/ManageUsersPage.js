import React, { useState } from "react";
import axios from "axios";
import "./ManageUserPage.css";

const Register = ({ refreshUsers }) => {
    const [formData, setFormData] = useState({
        username: "",
        email: "",
        phone: "",
        password: "",
        role: "player",
        birth_date: "",
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const validateForm = () => {
        const { username, email, phone, password, birth_date } = formData;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const phoneRegex = /^\+?[0-9]{10,15}$/;

        if (!username || !email || !phone || !password || !birth_date) {
            alert("Все поля должны быть заполнены!");
            return false;
        }
        if (!emailRegex.test(email)) {
            alert("Введите корректный адрес электронной почты!");
            return false;
        }
        if (!phoneRegex.test(phone)) {
            alert("Введите корректный номер телефона (10-15 цифр)!");
            return false;
        }
        if (password.length < 6) {
            alert("Пароль должен содержать минимум 6 символов!");
            return false;
        }
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) return;

        try {
            const response = await axios.post("http://localhost:8080/api/v1/user/register", formData);
            if (response.status === 200) {
                alert("Пользователь успешно зарегистрирован!");
                setFormData({
                    username: "",
                    email: "",
                    phone: "",
                    password: "",
                    role: "player",
                    birth_date: "",
                });
                refreshUsers(); // Обновление списка пользователей, если нужно
            }
        } catch (error) {
            console.error("Ошибка регистрации пользователя:", error);
            alert("Не удалось зарегистрировать пользователя.");
        }
    };

    return (
        <div className="register-container">
            <h2>Регистрация пользователя</h2>
            <form className="register-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Имя пользователя:</label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Телефон:</label>
                    <input
                        type="tel"
                        name="phone"
                        value={formData.phone}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Пароль:</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Роль:</label>
                    <select
                        name="role"
                        value={formData.role}
                        onChange={handleInputChange}
                    >
                        <option value="player">Игрок</option>
                        <option value="coach">Тренер</option>
                        <option value="admin">Администратор</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Дата рождения:</label>
                    <input
                        type="date"
                        name="birth_date"
                        value={formData.birth_date}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit" className="register-button">Зарегистрировать</button>
            </form>
        </div>
    );
};

export default Register;

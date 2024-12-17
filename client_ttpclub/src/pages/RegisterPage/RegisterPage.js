import React, { useState } from "react";
import axios from "axios";
import "./Register.css"; // Подключение CSS

const Register = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("player");
    const [birthDate, setBirthDate] = useState(""); // Новый state для даты рождения

    const handleSubmit = async (event) => {
        event.preventDefault();

        const userData = {
            username,
            email,
            phone,
            password,
            role,
            birthDate, // Добавляем birthDate в отправляемые данные
        };

        try {
            const response = await axios.post("http://localhost:8080/api/v1/user/register", userData);
            if (response.status === 200) {
                console.log("User registered successfully");
                alert("Registration successful!");
            } else {
                console.log("Registration failed");
                alert("Failed to register user.");
            }
        } catch (error) {
            console.error("Error registering user", error);
            alert("An error occurred during registration.");
        }
    };

    return (
        <div className="register-container">
            <h2 className="register-header">Register</h2>
            <form className="register-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Phone:</label>
                    <input
                        type="tel"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Role:</label>
                    <select value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value="player">Player</option>
                        <option value="coach">Coach</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Birth Date:</label>
                    <input
                        type="date"
                        value={birthDate}
                        onChange={(e) => setBirthDate(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="register-button">Register</button>
            </form>
        </div>
    );
};

export default Register;

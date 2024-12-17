import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";

const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const loginData = {
            email,
            password,
        };

        try {
            const response = await axios.post("http://localhost:8080/api/v1/auth/login", loginData);
            if (response.status === 200) {
                console.log("Login successfuuuuuuuuuuul");
                alert("Login successful!");
                navigate("/");
            } else {
                console.log("Invalid credentials");
                alert("Invalid email or password");
            }
        } catch (error) {
            console.error("Login error", error);
            alert("An error occurred during login.");
        }
    };

    return (
        <div className="login-container">
            <h2 className="login-header">Вход</h2>
            <form className="login-form" onSubmit={handleSubmit}>
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
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="login-button">Войти</button>
            </form>
        </div>
    );
};

export default LoginPage;

import React, { useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, useNavigate } from "react-router-dom";
import StartPage from "./pages/StartPage/StartPage";
import RegisterPage from "./pages/RegisterPage/RegisterPage";
import LoginPage from "./pages/LoginPage/LoginPage";
import MainPage from "./pages/MainPage/MainPage";
import AdminPage from "./pages/AdminPage/AdminPage";
import ProfilePage from "./pages/ProfilePage/ProfilePage";
import ManageUserPage from "./pages/AdminPage/ManageUsers/ManageUsersPage";
import setupAxiosInstance from "./config/Axios/axiosConfig";
import ManageEvent from "./pages/AdminPage/ManageEvents/ManageEvent";
import ManageTournaments from "./pages/ManageTournaments/ManageTournaments"
import ManageTrainings from "./pages/ManageTrainings/ManageTrainings"

const App = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const handleSessionExpired = () => {
            alert("Session Expired");
            localStorage.clear();
            navigate("/");
        };
        const axiosInstance = setupAxiosInstance(handleSessionExpired);
    }, [navigate]);

    return (
        <Routes>
            <Route path="/" element={<StartPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/main" element={<MainPage />} />
            <Route path="/tournaments_reg" element={<ManageTournaments />} />
            <Route path="/trainings_reg" element={<ManageTrainings />} />
            <Route path="/admin/:username" element={<AdminPage />} />
            <Route path="/profile/:username" element={<ProfilePage />} />
            <Route path="/admin/:username/manage-users" element={<ManageUserPage />} />
            <Route path="/admin/:username/manage-tournaments" element={<ManageEvent />} />
        </Routes>
    );
};

const AppWrapper = () => (
    <Router>
        <App />
    </Router>
);

export default AppWrapper;

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import StartPage from './pages/StartPage/StartPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import LoginPage from './pages/LoginPage/LoginPage';
import MainPage from './pages/MainPage/MainPage';
import AdminPage from './pages/AdminPage/AdminPage';
import ProfilePage from './pages/ProfilePage/ProfilePage';
import ManageUserPage from './pages/AdminPage/ManageUsers/ManageUsersPage';


const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<StartPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/main" element={<MainPage />} />
                <Route path="/admin/:username" element={<AdminPage />} />
                <Route path="/profile/:username" element={<ProfilePage />} />
                <Route path="/admin/:username/manage-users" element={<ManageUserPage />} />
            </Routes>
        </Router>
    );
};


export default App;

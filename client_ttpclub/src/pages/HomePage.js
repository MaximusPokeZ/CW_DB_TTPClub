import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../components/button';

const HomePage = () => {
    const navigate = useNavigate();

    const handleRegistrationClick = () => {
        navigate('/register');
    };

    return (
        <div style={{ textAlign: 'center', marginTop: '100px' }}>
            <h1>Добро пожаловать в TTP Club</h1>
            <p>Зарегистрируйтесь, чтобы стать частью нашего клуба!</p>
            <Button onClick={handleRegistrationClick} text="Регистрация" />
        </div>
    );
};

export default HomePage;

import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/button';

const StartPage = () => {
    const navigate = useNavigate();

    const handleRegistrationClick = () => {
        navigate('/register');
    };

    const handleLoginClick = () => {
        navigate('/login'); // Переход на страницу входа
    };

    return (
        <div style={{
            textAlign: 'center',
            marginTop: '100px',
            backgroundColor: '#f5f5f5',
            padding: '50px',
            borderRadius: '10px',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
            fontFamily: 'Arial, sans-serif'
        }}>
            <h1 style={{ color: '#333', marginBottom: '20px' }}>Добро пожаловать в TTP Club</h1>
            <p style={{ fontSize: '18px', color: '#666', marginBottom: '40px' }}>
                Зарегистрируйтесь или войдите, чтобы стать частью нашего клуба!
            </p>
            <div style={{ display: 'flex', justifyContent: 'center', gap: '20px' }}>
                <Button onClick={handleRegistrationClick} text="Регистрация" />
                <Button onClick={handleLoginClick} text="Войти" />
            </div>
        </div>
    );
};

export default StartPage;

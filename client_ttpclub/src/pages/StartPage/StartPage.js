import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/button';

const StartPage = () => {
    const navigate = useNavigate();

    const handleRegistrationClick = () => {
        navigate('/register');
    };

    const handleLoginClick = () => {
        navigate('/login');
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
            <h1 style={{ color: '#333', marginBottom: '20px' }}>Welcome to TTP Club</h1>
            <p style={{ fontSize: '18px', color: '#666', marginBottom: '40px' }}>
                Register or login to become part of our club!
            </p>
            <div style={{ display: 'flex', justifyContent: 'center', gap: '20px' }}>
                <Button onClick={handleRegistrationClick} text="Registration" />
                <Button onClick={handleLoginClick} text="Login" />
            </div>
        </div>
    );
};

export default StartPage;

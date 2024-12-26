import React, { useEffect, useState } from "react";
import {useParams } from "react-router-dom";
import { axiosInstance } from "../../config/Axios/axiosConfig";
import "./ProfilePage.css";

const ClientPage = () => {
    const { username } = useParams();
    const [userData, setUserData] = useState({});

    useEffect(() => {
        const fetchClientData = async () => {
            try {
                const response = await axiosInstance.get(`http://localhost:8080/api/v1/user/get_username/${username}`);
                setUserData(response.data);
            } catch (error) {
                console.error("Error fetching admin data", error);
                alert("User data could not be uploaded");
            }
        };
        fetchClientData();
    }, [username]);



    return (
        <div className="player-page-container">
            <h1>Player Page</h1>
            <div className="player-info">
                <p><strong>Name:</strong> {userData.username}</p>
                <p><strong>Email:</strong> {userData.email}</p>
                <p><strong>Phone:</strong> {userData.phone}</p>
                <p><strong>Role:</strong> {userData.role}</p>
                <p><strong>Rating:</strong> {userData.rating}</p>
            </div>
        </div>
    );
};

export default ClientPage;

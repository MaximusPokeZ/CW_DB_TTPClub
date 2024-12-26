import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { axiosInstance } from "../../config/Axios/axiosConfig";
import "./AdminPage.css";

const AdminPage = () => {
    const { username } = useParams();
    const [userData, setUserData] = useState({});
    const [selectedFile, setSelectedFile] = useState(null);
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
            alert(`Database dump saved`);
        } catch (error) {
            console.error("Error creating dump", error);
            alert("Error creating database dump.");
        }
    };

    const handleRestoreDatabase = async () => {
        if (!selectedFile) {
            alert("Please select a file to restore.");
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
            alert("The database was successfully restored.");
        } catch (error) {
            console.error("Error while restoring database", error);
            alert("Error while restoring database");
        }
    };

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    return (
        <div className="admin-page-container">
            <h1>Admin page</h1>
            <div className="admin-info">
                <p><strong>Name:</strong> {userData.username}</p>
                <p><strong>Email:</strong> {userData.email}</p>
                <p><strong>Phone:</strong> {userData.phone}</p>
                <p><strong>Role:</strong> {userData.role}</p>
                <p><strong>Rating:</strong> {userData.rating}</p>
            </div>
            <div className="admin-actions">
                <button onClick={() => navigate(`/admin/${username}/manage-users`)}>User Management</button>
                <button onClick={() => navigate(`/admin/${username}/manage-tournaments`)}>Event Management</button>
                <div>
                    <h3>Save database dump</h3>
                    <button onClick={handleDumpDatabase}>Save dump</button>
                </div>
                <div>
                    <h3>Restore Database</h3>
                    <input type="file" onChange={handleFileChange} />
                    <button onClick={handleRestoreDatabase}>Restore dump</button>
                </div>
            </div>
        </div>
    );
};

export default AdminPage;

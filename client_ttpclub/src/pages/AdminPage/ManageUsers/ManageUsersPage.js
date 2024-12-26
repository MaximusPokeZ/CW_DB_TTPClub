import React, { useState, useEffect } from "react";
import { axiosInstance } from "../../../config/Axios/axiosConfig";
import "./ManageUserPage.css";

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("player");
    const [birthDate, setBirthDate] = useState("");
    const [age, setAge] = useState("");
    const [rating, setRating] = useState("");
    const [createdAt, setCreatedAt] = useState("");
    const [editingUserId, setEditingUserId] = useState(null);
    const [isEditing, setIsEditing] = useState(false);

    const fetchUsers = async () => {
        try {
            const response = await axiosInstance.get("http://localhost:8080/api/v1/user");
            setUsers(response.data);
        } catch (error) {
            console.error("Error fetching users", error);
            alert("Error loading users.");
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const userData = {
            username,
            email,
            phone,
            password,
            role,
            birthDate,
            rating,
        };

        try {
            if (isEditing) {
                await axiosInstance.put(`http://localhost:8080/api/v1/user/update_user/${editingUserId}`, userData);
                alert("User updated successfully!");
            } else {
                await axiosInstance.post("http://localhost:8080/api/v1/user/register", userData);
                alert("User added successfully!");
            }
            fetchUsers();
            resetForm();
        } catch (error) {
            console.error("Error saving user", error);
            alert("Error saving user.");
        }
    };

    const handleEdit = (user) => {
        setEditingUserId(user.id);
        setUsername(user.username);
        setEmail(user.email);
        setPhone(user.phone);
        setRole(user.role);
        setBirthDate(user.birthDate ? user.birthDate.split("T")[0] : "");
        setRating(user.rating);
        setIsEditing(true);
    };

    const handleDelete = async (userId) => {
        if (window.confirm("Are you sure you want to delete this user?")) {
            try {
                await axiosInstance.delete(`http://localhost:8080/api/v1/user/delete_user/${userId}`);
                alert("User deleted successfully!");
                fetchUsers();
            } catch (error) {
                console.error("Error deleting user", error);
                alert("Error deleting user.");
            }
        }
    };

    const resetForm = () => {
        setEditingUserId(null);
        setUsername("");
        setEmail("");
        setPhone("");
        setPassword("");
        setRole("player");
        setBirthDate("");
        setRating("");
        setIsEditing(false);
    };

    return (
        <div className="user-management-container">
            <h2>User Management</h2>

            <div className="form-container">
                <h3>{isEditing ? "Edit User" : "Add New User"}</h3>
                <form className="user-form" onSubmit={handleSubmit}>
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
                    {!isEditing && (
                        <div className="form-group">
                            <label>Password:</label>
                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>
                    )}
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
                    <div className="form-group">
                        <label>Rating:</label>
                        <input
                            type="number"
                            value={rating}
                            onChange={(e) => setRating(e.target.value)}
                            required
                            min="0"
                            max="2000"
                            step="1"
                        />
                    </div>
                    <div className="form-buttons">
                        <button type="submit" className="save-button">
                            {isEditing ? "Update" : "Add"}
                        </button>
                        {isEditing && (
                            <button
                                type="button"
                                className="cancel-button"
                                onClick={resetForm}
                            >
                                Cancel
                            </button>
                        )}
                    </div>
                </form>
            </div>

            <h3>User List</h3>
            <button className="refresh-button" onClick={fetchUsers}>
                Refresh List
            </button>
            <table className="user-table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Birth Date</th>
                    <th>Age</th>
                    <th>Rating</th>
                    <th>Date of creation</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td>{user.username}</td>
                        <td>{user.email}</td>
                        <td>{user.phone}</td>
                        <td>{user.role}</td>
                        <td>{user.birthDate ? user.birthDate.split("T")[0] : ""}</td>
                        <td>{user.age}</td>
                        <td>{user.rating}</td>
                        <td>{user.createdAt ? new Date(user.createdAt).toLocaleString() : ""}</td>
                        <td>
                            {user.role !== "admin" && (
                                <>
                                    <button onClick={() => handleEdit(user)}>Edit</button>
                                    <button onClick={() => handleDelete(user.id)}>Delete</button>
                                </>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserManagement;

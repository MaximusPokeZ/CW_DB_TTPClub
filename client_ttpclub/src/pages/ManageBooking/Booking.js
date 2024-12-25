import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Booking.css";
import { axiosInstance } from "../../config/Axios/axiosConfig";

const Booking = () => {
    const [equipment, setEquipment] = useState([]);
    const [bookings, setBookings] = useState([]);
    const [selectedEquipment, setSelectedEquipment] = useState(null);
    const [selectedDate, setSelectedDate] = useState("");
    const [selectedTime, setSelectedTime] = useState("");
    const [duration, setDuration] = useState(1);
    const [availableTimes, setAvailableTimes] = useState([]);
    const [userId, setUserId] = useState("");
    const [isAdmin, setIsAdmin] = useState(false);

    const fetchEquipment = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/v1/equipment");
            setEquipment(response.data);
        } catch (error) {
            console.error("Error fetching equipment:", error);
            alert("Could not load equipment. Please try again later.");
        }
    };

    const fetchBookings = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/v1/booking");
            setBookings(response.data);
        } catch (error) {
            console.error("Error fetching bookings:", error);
        }
    };

    const fetchUserData = async () => {
        const email = localStorage.getItem("userEmail");
        try {
            const response = await axiosInstance.get(
                `http://localhost:8080/api/v1/user/get_email/${email}`
            );
            setUserId(response.data.id);
            setIsAdmin(response.data.role === "admin");
        } catch (error) {
            console.error("Error fetching user data:", error);
            alert("Couldn't upload user data.");
        }
    };

    useEffect(() => {
        fetchEquipment();
        fetchUserData();
        fetchBookings();
    }, []);

    useEffect(() => {
        const times = [];
        const now = new Date();
        const selectedDay = new Date(selectedDate);

        const currentHour = now.getHours();
        const isToday = selectedDay.toDateString() === now.toDateString();

        const startHour = isToday && currentHour < 20 ? currentHour : 8;

        for (let hour = startHour; hour < 20; hour++) {
            times.push(`${hour}:00`);
            times.push(`${hour}:30`);
        }

        setAvailableTimes(times);
    }, [selectedDate]);

    const calculateEndTime = (startTime, duration) => {
        const [hours, minutes] = startTime.split(":").map(Number);
        const endHours = hours + duration;
        return `${endHours}:${minutes.toString().padStart(2, "0")}`;
    };

    const handleBooking = async () => {
        if (!selectedEquipment || !selectedDate || !selectedTime) {
            alert("Please select all booking details.");
            return;
        }

        const endTime = calculateEndTime(selectedTime, duration);

        const formatTime = (time) => {
            const [hours, minutes] = time.split(":").map((t) => t.padStart(2, "0"));
            return `${hours}:${minutes}`;
        };

        const bookingData = {
            userId,
            equipmentId: selectedEquipment,
            startTime: `${selectedDate}T${formatTime(selectedTime)}`,
            endTime: `${selectedDate}T${formatTime(endTime)}`,
        };


        try {
            await axios.post("http://localhost:8080/api/v1/booking", bookingData);
            fetchBookings();
            fetchEquipment();
            alert("Booking successful!");
        } catch (error) {
            console.error("Error making booking:", error);
            alert("Could not complete the booking. Please try again.");
        }
    };

    const handleDeleteBooking = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/v1/booking/${id}`);
            fetchBookings();
            fetchEquipment();
            alert("Booking deleted successfully!");
        } catch (error) {
            console.error("Error deleting booking:", error);
            alert("Could not delete the booking. Please try again.");
        }
    };

    const handleChangeStatus = async (id, status) => {
        try {
            await axios.patch(`http://localhost:8080/api/v1/booking/${id}`, { status });
            setBookings((prev) =>
                prev.map((booking) =>
                    booking.id === id ? { ...booking, status } : booking
                )
            );
            alert(`Booking status changed to ${status}!`);
        } catch (error) {
            console.error("Error updating booking status:", error);
            alert("Could not update booking status. Please try again.");
        }
    };

    const handleRefreshBookings = () => {
        fetchBookings();
        fetchEquipment();
    };

    return (
        <div className="booking-container">
            <h2>Book Equipment</h2>
            <div className="booking-form">
                <label>
                    Select Equipment:
                    <select
                        value={selectedEquipment}
                        onChange={(e) => setSelectedEquipment(e.target.value)}
                    >
                        <option value="">-- Select Equipment --</option>
                        {equipment.map((item) => (
                            <option key={item.id} value={item.id}>
                                {item.name} - {item.type}
                            </option>
                        ))}
                    </select>
                </label>
                <label>
                    Select Date:
                    <input
                        type="date"
                        value={selectedDate}
                        min={new Date().toISOString().split("T")[0]}
                        onChange={(e) => setSelectedDate(e.target.value)}
                    />
                </label>
                <label>
                    Select Time:
                    <select
                        value={selectedTime}
                        onChange={(e) => setSelectedTime(e.target.value)}
                    >
                        <option value="">-- Select Time --</option>
                        {availableTimes.map((time, index) => (
                            <option key={index} value={time}>
                                {time}
                            </option>
                        ))}
                    </select>
                </label>
                <label>
                    Duration (hours):
                    <input
                        type="number"
                        min="1"
                        max="12"
                        value={duration}
                        onChange={(e) => setDuration(Number(e.target.value))}
                    />
                </label>
                <button onClick={handleBooking} className="booking-button">
                    Book Now
                </button>
            </div>

            {isAdmin && (
                <div className="admin-booking-table">
                    <h2>All Bookings</h2>
                    <button onClick={handleRefreshBookings} className="refresh-button">
                        Refresh Bookings
                    </button>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>User ID</th>
                            <th>Equipment ID</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {bookings.map((booking) => (
                            <tr key={booking.id}>
                                <td>{booking.id}</td>
                                <td>{booking.userId}</td>
                                <td>{booking.equipmentId}</td>
                                <td>{booking.startTime}</td>
                                <td>{booking.endTime}</td>
                                <td>{booking.status}</td>
                                <td>
                                    <button
                                        onClick={() => handleChangeStatus(booking.id, "confirm")}
                                        className="confirm-button"
                                    >
                                        Confirm
                                    </button>
                                    <button
                                        onClick={() => handleChangeStatus(booking.id, "cancel")}
                                        className="cancel-button"
                                    >
                                        Cancel
                                    </button>
                                    <button
                                        onClick={() => handleDeleteBooking(booking.id)}
                                        className="delete-button"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default Booking;

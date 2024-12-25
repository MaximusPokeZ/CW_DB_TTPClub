import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Booking.css";
import { axiosInstance } from "../../config/Axios/axiosConfig";

const Booking = () => {
    const [equipment, setEquipment] = useState([]);
    const [selectedEquipment, setSelectedEquipment] = useState(null);
    const [selectedDate, setSelectedDate] = useState("");
    const [selectedTime, setSelectedTime] = useState("");
    const [duration, setDuration] = useState(1);
    const [availableTimes, setAvailableTimes] = useState([]);
    const [userId, setUserId] = useState("");

    useEffect(() => {
        const fetchEquipment = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/v1/equipment");
                setEquipment(response.data);
            } catch (error) {
                console.error("Error fetching equipment:", error);
                alert("Could not load equipment. Please try again later.");
            }
        };

        const fetchUserData = async () => {
            const email = localStorage.getItem("userEmail");
            try {
                const response = await axiosInstance.get(
                    `http://localhost:8080/api/v1/user/get_email/${email}`
                );
                setUserId(response.data.id);
            } catch (error) {
                console.error("Error fetching user data:", error);
                alert("Couldn't upload user data.");
            }
        };

        fetchEquipment();
        fetchUserData();
    }, []);

    useEffect(() => {
        const times = [];
        for (let hour = 8; hour < 20; hour++) {
            times.push(`${hour}:00`);
            times.push(`${hour}:30`);
        }
        setAvailableTimes(times);
    }, []);

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

        const bookingData = {
            userId,
            equipmentId: selectedEquipment,
            startTime: `${selectedDate}T${selectedTime}`,
            endTime: `${selectedDate}T${endTime}`,
        };

        try {
            await axios.post("http://localhost:8080/api/v1/booking", bookingData);
            alert("Booking successful!");
        } catch (error) {
            console.error("Error making booking:", error);
            alert("Could not complete the booking. Please try again.");
        }
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
        </div>
    );
};

export default Booking;

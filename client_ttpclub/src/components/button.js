import React from "react"

const Button = ({ onClick, text }) => {
    return (
        <button
         style={{
             padding: "10px",
             fontSize: "14px",
             cursor: "pointer",
             backgroundColor: "#007bff",
             color: "#fff",
             border: "none",
             borderRadius: "5px",
         }}
        onClick={onClick}
        >
            {text}
            </button>
    );
};

export default Button;
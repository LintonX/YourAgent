import React, { useContext, useState } from "react";
import "../../../App.css";
import "./clientDetailUnit.css";
import { ClientContext, userInfo } from "../ClientSearchDetailCarousel";
import PrimaryButton from "../../PrimaryButton/PrimaryButton";
import AIBubble from "../../AIBubble/AIBubble";

function ClientDetailUnit() {
  const { quickFact, setUserInfo } = useContext(ClientContext);

  const [formData, setFormData] = useState<userInfo>({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = () => {
    console.log(formData);
    if (formData.firstName && formData.email.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)) {
      setUserInfo(formData);
    }
  };

  return (
    <div>
      <h1 className="details-header">
        How should your agent contact&nbsp;<span style={{ color: "var(--quaternary-color)" }}>you</span>?
      </h1>
      <div className="details-container">
        <form
          className="form"
          onSubmit={(event) => {
            event.preventDefault();
          }}
        >
          <div className="firstName-lastName-container">
            <input
              className="firstName-input"
              type="text"
              name="firstName"
              placeholder="First Name"
              required
              value={formData.firstName}
              onChange={handleChange}
            />
            <input
              className="lastName-input"
              type="text"
              name="lastName"
              placeholder="Optional: Last Name"
              value={formData.lastName}
              onChange={handleChange}
            />
          </div>
          <div className="email-phoneNumber-container">
            <input
              className="email-input"
              type="email"
              name="email"
              placeholder="Email"
              required
              value={formData.email}
              onChange={handleChange}
            />
            <input
              className="phoneNumber-input"
              type="phone"
              name="phoneNumber"
              placeholder="Optional: Phone number"
              value={formData.phoneNumber}
              onChange={handleChange}
            />
          </div>
          <div>
            <PrimaryButton
              text="Submit"
              className="btn primary"
              onClick={handleSubmit}
            />
          </div>
        </form>
        <div className="ai-bubble-container-details">
          {quickFact !== "" && <AIBubble quickFact={quickFact || "Loading..."}/>}
        </div>
      </div>
      {/* {county} */}
    </div>
  );
}

export default ClientDetailUnit;

import React, { useContext, useState } from "react";
import "../../../App.css";
import "./clientDetailUnit.css";
import { CountyContext, userInfo } from "../ClientSearchDetailCarousel";
import PrimaryButton from "../../PrimaryButton/PrimaryButton";

function ClientDetailUnit() {
  const { county, setUserInfo } = useContext(CountyContext);

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
    setUserInfo(formData);
  };

  return (
    <div>
      <h1 className="details-header">
        How should they contact{" "}
        <span style={{ color: "var(--quaternary-color)" }}>you</span>?
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
      </div>
      here
      {county}
    </div>
  );
}

export default ClientDetailUnit;

import React, { useContext, useEffect, useState } from "react";
import "../../../App.css";
import "./clientDetailUnit.css";
import { ClientContext, userInfo } from "../ClientSearchDetailCarousel";
import PrimaryButton from "../../PrimaryButton/PrimaryButton";
import AIBubble from "../../AIBubble/AIBubble";
import { SERVER } from "../../../constants";

function ClientDetailUnit() {
  const { place, setUserInfo } = useContext(ClientContext);
  const [quickFact, setQuickFact] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(true);

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

  useEffect(() => {
    const callQuickFact = async () => { 
      try {
        if (place) {
          setIsLoading(true);
          const response = await fetch(`${SERVER}locationFact?place=${place}`);

          if (response.ok) { 
            const content = await response.text()
            setQuickFact(content);
            setIsLoading(false)
          } else {
            console.error(`Failed to fetch fact: ${response.status} - ${response.statusText}`);
          }
        }
      } catch (error) {
        console.error("Network or server error occurred:", error);
      }
    };
  
    callQuickFact(); 
  }, [place]);

  return (
    <div>
      <h1 className="details-header">
        How should they contact&nbsp;<span style={{ color: "var(--quaternary-color)" }}>you</span>?
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
        <div className="ai-bubble-container-details">
          {!isLoading && <AIBubble quickFact={quickFact || "Loading..."}/>}
        </div>
      </div>
      {/* {county} */}
    </div>
  );
}

export default ClientDetailUnit;

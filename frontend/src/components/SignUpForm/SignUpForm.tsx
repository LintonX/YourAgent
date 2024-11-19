import React, { useState } from "react";
import "../../App.css";
import "../SignUpForm/signUpForm.css";
import PrimaryButton from "../PrimaryButton/PrimaryButton";
import { SERVER } from "../../constants";
import { useNavigate } from "react-router-dom";

function SignUpForm() {

  const navigate = useNavigate();

  const [formDetails, setFormDetails] = useState({
    email: "",
    password: "",
    confirmed_password: "",
  });

  const handleOnChange = (key: string, value: string) => {
    setFormDetails((prevDetails) => ({
      ...prevDetails,
      [key]: value,
    }));
  };

  const [passwordErrorMsg, setPasswordErrorMsg] = useState("");

  const handleFormSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (formDetails.password !== formDetails.confirmed_password) {
      setPasswordErrorMsg("Passwords must match.");
    } else {
      setPasswordErrorMsg("");
    
      const response = await fetch(`${SERVER}auth/createAgentAccount`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: formDetails.email,
          pass: formDetails.password
        }),
      });

      const body = await response.text();

      if (body) {
        console.log("in here")
        navigate(`/auth/signin`)
        window.location.reload();
      }
    }
  };

  return (
    <>
      <div className="title-container">
        <h4>Create an account</h4>
        <p>Enter your email and password</p>
      </div>
      <div className="form-container">
        <form className="form-object" onSubmit={handleFormSubmit}>
          <div style={{ height: "12px" }}></div>
          <input
            type="email"
            placeholder="youragent@example.com"
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              handleOnChange("email", e.target.value);
            }}
          />
          <div style={{ height: "12px" }}></div>
          <span style={{ color: "red" }}>{passwordErrorMsg}</span>
          <input
            type="password"
            placeholder="password"
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              handleOnChange("password", e.target.value);
            }}
          />
          <div style={{ height: "12px" }}></div>
          <input
            type="password"
            placeholder="confirm password"
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              handleOnChange("confirmed_password", e.target.value);
            }}
          />
          <div style={{ height: "14px" }}></div>
          <div className="radio-btn-container">
            <label>
              <input name="tos-privacy-policy" type="radio" required />
              <span>&nbsp;&nbsp;I accept the TOS and Privacy Policy</span>
            </label>
          </div>
          <div style={{ height: "14px" }}></div>
          <PrimaryButton
            style={{ width: "100%" }}
            text="Create account"
            className="btn primary"
            onClick={() => console.log()}
          />
          <div style={{ height: "12px" }}></div>
        </form>
      </div>
    </>
  );
}

export default SignUpForm;

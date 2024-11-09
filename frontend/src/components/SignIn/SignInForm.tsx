import React, { useState } from "react";
import "../../App.css";
import "../SignIn/signInForm.css";
import PrimaryButton from "../PrimaryButton/PrimaryButton";

function SignInForm() {
  const [formDetails, setFormDetails] = useState({
    email: "",
    password: "",
    confirmedPassword: "",
  });

  const handleOnChange = (key: string, value: string) => {
    setFormDetails((prevDetails) => ({
      ...prevDetails,
      [key]: value,
    }));
  };

  const [passwordErrorMsg, setPasswordErrorMsg] = useState("");

  const handleFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (formDetails.password !== formDetails.confirmedPassword) {
      setPasswordErrorMsg("Passwords must match.");
    } else {
      setPasswordErrorMsg("");
    }
  }

  return (
    <>
      <div className="title-container">
        <h4>Create an account</h4>
        <p>Enter your email and password</p>
      </div>
      <div className="form-container">
        <form className="form-object" onSubmit={handleFormSubmit}>
          <div style={{ height: "8px" }}></div>
          <input
            type="email"
            placeholder="youragent@example.com"
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              handleOnChange("email", e.target.value);
            }}
          />
          <div style={{ height: "12px" }}></div>
          <span style={{color:"red"}}>{passwordErrorMsg}</span>
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
              handleOnChange("confirmedPassword", e.target.value);
            }}
          />
          <div style={{ height: "14px" }}></div>
          <div className="radio-btn-container">
            <label>
              <input name="tos-privacy-policy" type="radio" />
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
          <p>
            Already have an account? <a href="">Sign in</a>{" "}
          </p>
        </form>
      </div>
    </>
  );
}

export default SignInForm;

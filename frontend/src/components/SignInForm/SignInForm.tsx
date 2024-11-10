import React, { useState } from "react";
import '../../App.css'
import '../SignInForm/signInForm.css'
import PrimaryButton from "../PrimaryButton/PrimaryButton";

function SignUpForm() {
  const [formDetails, setFormDetails] = useState({
    email: "",
    password: "",
  });

  const handleOnChange = (key: string, value: string) => {
    setFormDetails((prevDetails) => ({
      ...prevDetails,
      [key]: value,
    }));
  };

  const [passwordErrorMsg, setPasswordErrorMsg] = useState("");

  const handleFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {};

  return (
    <>
      <div className="title-container">
        <h4>Sign in</h4>
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
          <input
            type="password"
            placeholder="password"
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              handleOnChange("password", e.target.value);
            }}
          />
          <span style={{ color: "red" }}>{passwordErrorMsg}</span>
          <div style={{ height: "14px" }}></div>
          <PrimaryButton
            style={{ width: "100%" }}
            text="Sign in"
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

import React, { useState } from "react";
import '../../App.css'
import '../SignInForm/signInForm.css'
import PrimaryButton from "../PrimaryButton/PrimaryButton";
import { SERVER } from "../../constants";
import { useNavigate } from "react-router";

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

  const navigate = useNavigate();

  const handleNavigate = (agent: any) => {
    navigate('/agents/dashboard', {
      state: {
        authorized: true,
        agentContext: agent
      }, 
    });
  };

  const handleFormSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    const response = await fetch(`${SERVER}auth/signin`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: formDetails.email,
        pass: formDetails.password
      }),
      // credentials: 'include'
    });

    const status = response.status;
    
    if (status === 200) {
      setPasswordErrorMsg("");
      const agent = JSON.parse(await response.text());
      console.log(agent)
      handleNavigate(agent);
    } else {
      setPasswordErrorMsg("Invalid email or password.")
    }
  };

  return (
    <>
      <div className="title-container">
        <h4>Sign in</h4>
        <p>Enter your email and password</p>
      </div>
      <div className="form-container">
        <form className="form-object" onSubmit={handleFormSubmit}>
          <div style={{ height: "8px" }}></div>
          <span style={{ color: "red" }}>{passwordErrorMsg}</span>
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

import React from "react";
import "../../App.css";
import "../AuthenticationView/authentication.css";
import NavBar from "../../components/NavBar/NavBar";
import SignInForm from "../../components/SignIn/SignInForm";

function Authentication() {
  return (
    <>
      <NavBar navBarItems={[]} />
      <div className="outer-container">
        <div className="main-container">
          <SignInForm />
        </div>
      </div>
    </>
  );
}

export default Authentication;

import React, { useState } from "react";
import "../../App.css";
import "../AuthenticationView/authentication.css";
import NavBar from "../../components/NavBar/NavBar";
import SignUpForm from "../../components/SignUpForm/SignUpForm";
import SignInForm from "../../components/SignInForm/SignInForm";
import { useNavigate, useParams } from "react-router";
import Footer from "../../components/Footer/Footer";

function Authentication() {

  const navigate = useNavigate();

  const navigateTo = (route: string) => {
    navigate(`/auth/${route}`);
  };

  const authView = useParams().view;

  return (
    <>
      <NavBar navBarItems={[]} />
      <div className="outer-container">
        <div className="main-authentication-container">
          {authView === 'signup' ? (
            <>
              <SignUpForm />
              <p>
                Already have an account?{" "}
                <span
                  className="auth-view-text"
                  onClick={() => navigateTo('signin')}
                >
                  Sign in
                </span>
              </p>
            </>
          ) : (
            <>
              <SignInForm />
              <p>
                Need to create an account?{" "}
                <span
                  className="auth-view-text"
                  onClick={() => navigateTo('signup')}
                >
                  Sign up
                </span>
              </p>
            </>
          )}
        </div>
      </div>
      <Footer />
    </>
  );
}

export default Authentication;

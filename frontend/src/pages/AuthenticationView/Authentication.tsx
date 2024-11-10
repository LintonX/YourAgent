import React, { useState } from "react";
import "../../App.css";
import "../AuthenticationView/authentication.css";
import NavBar from "../../components/NavBar/NavBar";
import SignUpForm from "../../components/SignUpForm/SignUpForm";
import SignInForm from "../../components/SignInForm/SignInForm";
import { useParams } from "react-router";
import Footer from "../../components/Footer/Footer";

function Authentication() {

  const [authView, setAuthView] = useState(useParams().view);

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
                  onClick={() => setAuthView("signin")}
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
                  onClick={() => setAuthView("signup")}
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

import React from "react";
import "./App.css";
import PricingView from "./screens/PricingView/PricingView";
import NavBar from "./components/NavBar/NavBar";
// import SignUpView from "./screens/SignUpView/SignUpView";

function App() {
  return (
    <>
      <NavBar />
      <PricingView/>
      {/* <SignUpView /> */}
    </>
  );
}

export default App;

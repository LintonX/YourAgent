import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import PricingView from "./pages/PricingView/PricingView";
import ClientView from "./pages/ClientView/ClientView";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path={'/'} Component={ClientView}/>
          <Route path={'/Agents'} Component={PricingView}/>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;

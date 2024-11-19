import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import PricingView from "./pages/PricingView/PricingView";
import ClientView from "./pages/ClientView/ClientView";
import Authentication from "./pages/AuthenticationView/Authentication";
import AgentDashboard from "./pages/AgentDashboardView/AgentDashboard";
import ScrollToTop from "./components/ScrollToTop/ScrollToTop";

function App() {
  return (
    <>
      <BrowserRouter>
        <ScrollToTop>
          <Routes>
            <Route path="/" Component={ClientView} />
            <Route path="/agents" Component={PricingView} />
            <Route path="/agents/dashboard" Component={AgentDashboard} />
            <Route path="/auth/:view" element={<Authentication />} />
            <Route path="/success" element={<div>success</div>}></Route>
            <Route path="/success" element={<div>failed</div>}></Route>
          </Routes>
        </ScrollToTop>
      </BrowserRouter>
    </>
  );
}

export default App;

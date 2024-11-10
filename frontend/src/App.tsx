import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import PricingView from "./pages/PricingView/PricingView";
import ClientView from "./pages/ClientView/ClientView";
import SelectionAgentView from "./pages/SelectionAgentView/SelectionAgentView";
import Authentication from "./pages/AuthenticationView/Authentication";
import AgentDashboard from "./pages/AgentDashboardView/AgentDashboard";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path={'/'} Component={ClientView}/>
          <Route path={'/Agents'} Component={PricingView}/>
          <Route path={'/tier/:level'} Component={SelectionAgentView}/>
          <Route path="/auth/:view" element={<Authentication />} />
          <Route path="/Agents/dashboard" Component={AgentDashboard} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;

import React from "react";
import "../../App.css";
import "../AgentDashboardView/agentDashboard.css";
import NavBar from "../../components/NavBar/NavBar";
import { AgGridReact } from "ag-grid-react";
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { useState } from "react";
import { ColDef } from "ag-grid-community";
import Footer from "../../components/Footer/Footer";

function AgentDashboard() {
  const newestLead = {
    firstName: "Jane",
    lastName: "Doe",
    email: "jane.doe@example.com",
    phoneNumber: "301-555-555",
    interestedIn: "Germantown",
    county: "Montgomery",
    state: "MD",
  };

  const [rowData, setRowData] = useState([
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
    {
      firstName: "Jane",
      lastName: "Doe",
      email: "jane.doe@example.com",
      phoneNumber: "301-555-555",
      interestedIn: "Germantown",
      county: "Montgomery",
      state: "MD",
    },
  ]);

  const [colDefs, setColDefs] = useState<ColDef[]>([
    { field: "firstName", flex: 1.5 },
    { field: "lastName", flex: 1.5 },
    { field: "email", flex: 3 },
    { field: "phoneNumber", flex: 2 },
    { field: "interestedIn", flex: 2 },
    { field: "county", flex: 2 },
    { field: "state", flex: 1 },
  ]);

  return (
    <>
      <NavBar navBarItems={[]} />
      <div className="outer-dashboard-container">
        <div className="newest-lead-container">
          <h3 style={{ fontWeight: "800" }}>Newest lead</h3>
          <div>
            <p>
              {newestLead.firstName} {newestLead.lastName}
            </p>
            <p>
              {newestLead.email} {newestLead.phoneNumber}
            </p>
            <p>
              {newestLead.interestedIn}, {newestLead.state} | {newestLead.county}
            </p>
          </div>
        </div>
        <div className="leads-filtering-container">
          <h3 style={{ fontWeight: "800" }}>All leads</h3>
        </div>
        <div className="table-content-container">
          <div className="table-container">
            <AgGridReact
              className="ag-theme-quartz"
              rowData={rowData}
              columnDefs={colDefs}
              // autoSizeStrategy={{
              //     type: 'fitGridWidth',
              // }}
            />
          </div>
          <div className="export-csv-container">Export leads to CSV</div>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default AgentDashboard;

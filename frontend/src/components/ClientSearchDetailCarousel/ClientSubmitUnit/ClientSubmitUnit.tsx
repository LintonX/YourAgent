import React, { useContext } from "react";
import "../../../App.css";
import "./clientSubmitUnit.css";
import { ClientContext } from "../ClientSearchDetailCarousel";

function ClientCongratsUnit() {
  const { userInfo } = useContext(ClientContext);

  return (
    <div className="submit-container">
      <h1 className="submit-header">
        Thanks, <span style={{color:"var(--quaternary-color)"}}>{userInfo.firstName}</span>.<br />
        We are currently connecting you with an expert.
        <br />
        <br />
        Good luck with your search!
      </h1>
    </div>
  );
}

export default ClientCongratsUnit;

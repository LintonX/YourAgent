import React from "react";
import "../../../App.css";
import "./clientSearchUnit.css";
import LocationSearch from "../../LocationSearch/LocationSearch";
import { PLACE_AND_STATE_DATA } from "../../../Data/town_state_list";

function ClientSearchUnit() {
  return (
    <div className="search-header-container">
      <h1 className="search-header">
        Where are you searching?{<br />}
        Find a local{" "}
        <span style={{ color: "var(--quaternary-color)" }}>expert</span> in that
        area below.
      </h1>
      <div className="search-container">
        <LocationSearch suggestions={PLACE_AND_STATE_DATA} />
      </div>
    </div>
  );
}

export default ClientSearchUnit;

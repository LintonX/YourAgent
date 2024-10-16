import React, { useContext } from "react";
import { useState } from "react";
import "../../App.css";
import "../LocationSearch/locationSearch.css";
import { SEARCH_BUTTON_ICON, SERVER } from "../../constants";
import { CountyContext } from "../ClientSearchDetailCarousel/ClientSearchDetailCarousel";

type AutoCompleteProps = {
  suggestions: string[];
};

function LocationSearch({ suggestions }: AutoCompleteProps) {
  const { setCounty } = useContext(CountyContext);
  const [input, setInput] = useState<string>("");
  const [filteredSuggestions, setFilteredSuggestions] = useState<string[]>([]);
  const [showSuggestions, setShowSuggestions] = useState<boolean>(false);

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userInput = e.target.value;
    setInput(userInput);

    const filtered = new Set<string>(
      suggestions.filter((suggestion) =>
        suggestion.toLowerCase().startsWith(userInput.toLowerCase())
      )
    );

    setFilteredSuggestions([...filtered]);
    setShowSuggestions(true);
  };

  const handleOnClick = (suggestion: string) => {
    setInput(suggestion);
    setFilteredSuggestions([]);
    setShowSuggestions(false);
    const [place, state] = suggestion.split(",");
    fetchCounty(place, state);
  };

  const fetchCounty = async (place: string, state: string) => {
    const response = await fetch(
      `${SERVER}locationInquiry?place=${place}&state=${state}`
    );

    if (response.status === 200) {
      const countyFound = await response.text();
      console.log(countyFound);
      setCounty(countyFound)
    } else {
      console.log("errorroroorr");
    }
  };

  const renderSuggestions = () => {
    if (showSuggestions && input) {
      if (filteredSuggestions.length > 0) {
        return (
          <ul className="suggestions">
            {filteredSuggestions.map((suggestion, index) => {
              return (
                <li key={suggestion} onClick={() => handleOnClick(suggestion)}>
                  {suggestion}
                </li>
              );
            })}
          </ul>
        );
      } else {
        return <div className="no-suggestions">No suggestions available.</div>;
      }
    }
    return null;
  };

  return (
    <div className="autocomplete">
      <div className="search-bar-container">
        <input
          className="search-bar"
          type="text"
          onChange={handleOnChange}
          value={input}
          placeholder="Search..."
        />
        <button className="search-icon-button">
          <img
            className="search-icon"
            src={SEARCH_BUTTON_ICON}
            alt="Search Icon"
            onClick={() => handleOnClick(input)}
          />
        </button>
      </div>
      {renderSuggestions()}
    </div>
  );
}

export default LocationSearch;

import React, { useContext } from "react";
import { useState } from "react";
import "../../App.css";
import "../LocationSearch/locationSearch.css";
import { SEARCH_BUTTON_ICON, SERVER } from "../../constants";
import { ClientContext } from "../ClientSearchDetailCarousel/ClientSearchDetailCarousel";

type AutoCompleteProps = {
  suggestions: string[];
};

function LocationSearch({ suggestions }: AutoCompleteProps) {
  const { setCounty, setPlace, setQuickFact } = useContext(ClientContext);
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
    fetchQuickFact(suggestion);
    setPlace(suggestion);
  };

  const fetchCounty = async (place: string, state: string) => {
    try {
      const response = await fetch(
        `${SERVER}locationInquiry?place=${place}&state=${state}`
      );

      if (response.ok) {
        const countyFound = await response.text();
        setCounty(countyFound);
      } else {
        console.log(`Failed to fetch county: ${response.status} - ${response.statusText}`);
      }
    } catch (error) {
      console.error("Network or server error occurred:", error);
    }
  };

  const fetchQuickFact = async (place: string) => {
    try {
      const response = await fetch(
        `${SERVER}locationFact?place=${place}`
      );

      if (response.ok) {
        const content = await response.text();
        setQuickFact(content);
      } else {
        console.error(
          `Failed to fetch fact: ${response.status} - ${response.statusText}`
        );
      }
    } catch (error) {
      console.error("Network or server error occurred:", error);
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
          placeholder="Enter a town or city and state..."
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

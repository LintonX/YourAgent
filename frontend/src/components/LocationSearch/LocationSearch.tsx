import React from "react";
import { useState } from "react";
import "../../App.css";
import "../LocationSearch/locationSearch.css";
import { SEARCH_BUTTON_ICON } from "../../constants";

type AutoCompleteProps = {
  suggestions: string[];
};

function LocationSearch({ suggestions }: AutoCompleteProps) {
  const [input, setInput] = useState<string>("");
  const [filteredSuggestions, setFilteredSuggestions] = useState<string[]>([]);
  const [showSuggestions, setShowSuggestions] = useState<boolean>(false);
  //   const [activeSuggestionIndex, setActiveSuggestionIndex] = useState<number>(0);

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userInput = e.target.value;
    setInput(userInput);

    const filtered = new Set<string>(
      suggestions.filter((suggestion) =>
        suggestion.toLowerCase().startsWith(userInput.toLowerCase())
      )
    );

    setFilteredSuggestions([...filtered]);
    setShowSuggestions(true);
    // setActiveSuggestionIndex(0);
  };

  const onClick = (suggestion: string) => {
    setInput(suggestion);
    setFilteredSuggestions([]);
    setShowSuggestions(false);
  };


  const renderSuggestions = () => {
    if (showSuggestions && input) {
      if (filteredSuggestions.length > 0) {
        return (
          <ul className="suggestions">
            {filteredSuggestions.map((suggestion, index) => {
              return (
                <li key={suggestion} onClick={() => onClick(suggestion)}>
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
          onChange={onChange}
          value={input}
          placeholder="Search..."
        />
        <button className="search-icon-button">
          <img
            className="search-icon"
            src={SEARCH_BUTTON_ICON}
            alt="Search Icon"
          />
        </button>
      </div>
      {renderSuggestions()}
    </div>
  );
}

export default LocationSearch;

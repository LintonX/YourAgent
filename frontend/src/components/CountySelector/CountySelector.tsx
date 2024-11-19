import React, { useContext, useEffect, useState } from "react";
import "../../App.css";
import "../CountySelector/countySelector.css";
import { SelectionContext } from "../../pages/SelectionAgentView/SelectionAgentView";
import { COUNTY_QUANTITY_MAP } from "../../constants";

export type CountySelectorProps = {
  tier: string;
};

function CountySelector({ tier }: CountySelectorProps) {

  console.log(tier);

  const { state } = useContext(SelectionContext);
  const [counties, setCounties] = useState<[]>([]);

  const {
    selectedCounties,
    setSelectedCounties,
    setCountyQuantity,
  } = useContext(SelectionContext);

  const handleCountyAddSelection = (county: string) => {
    setSelectedCounties((prev) => {
      if (
        selectedCounties.length < COUNTY_QUANTITY_MAP[tier] &&
        !prev.includes(county)
      ) {
        const updatedCounties = [...prev, county];
        setCountyQuantity(COUNTY_QUANTITY_MAP[tier] - updatedCounties.length);
        return updatedCounties;
      }
      return prev;
    });
  };

  const handleCountyRemoveSelection = (county: string) => {
    setSelectedCounties((prev) => {
      const updatedCounties = prev.filter(
        (currCounty) => currCounty !== county
      );
      setCountyQuantity(COUNTY_QUANTITY_MAP[tier] - updatedCounties.length);

      return updatedCounties;
    });
  };

  const fetchCounties = async () => {
    try {
      const response = await fetch(
        `https://lintonx.github.io/StateCountiesCDN/${state
          .replace(" ", "%20")
          .toLowerCase()}.json`
      );

      if (!response.ok) {
        throw new Error(`Failed to fetch counties: ${response.statusText}`);
      }

      const counties = await response.json();
      console.log(counties);
      setCounties(counties);
    } catch (error) {
      console.error("Error fetching counties:", error);
    }
  };

  useEffect(() => {
      fetchCounties();
      // eslint-disable-next-line 
  }, []);

  return (
    <div className="county-selector-container">
      <div className="county-selector-box">
        <div className="total-county-box">
          <h3>Available Counties</h3>
          <div className="checkbox-list-unselected">
            {counties.map((county, index) => {
              const containsCounty = selectedCounties.includes(county);
              return (
                <div className="checkbox-item-box" key={county + String(index)}>
                  <label className="label-style">
                    <input
                      className="checkbox-style"
                      type="checkbox"
                      id={county + String(index)}
                      name={county + String(index)}
                      value={county}
                      checked={containsCounty}
                      onChange={() => {
                        if (containsCounty) {
                          handleCountyRemoveSelection(county);
                        } else {
                          handleCountyAddSelection(county);
                        }
                      }}
                    />
                    {county}
                  </label>
                </div>
              );
            })}
          </div>
        </div>
        <div className="selected-counties-box">
          <h3>Selected Counties</h3>
          <div className="checkbox-list-selected">
            {selectedCounties.map((county, index) => (
              <div
                className="checkbox-item-box"
                key={county + String(index)}
                onClick={() => handleCountyRemoveSelection(county)}
              >
                <label className="label-style">{county}</label>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default CountySelector;

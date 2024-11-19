import React, { useContext } from "react";
import '../../App.css'
import './mapStateSelector.css'

import {
  ComposableMap,
  Geographies,
  Geography,
  GeographyProps,
  Marker,
} from "react-simple-maps";
import { GEO_URL, US_STATE_ABBREVIATION_DATA } from './USMapStateData'
import { SelectionContext } from "../../pages/SelectionAgentView/SelectionAgentView";

interface GeographyPropsWithName extends GeographyProps {
    properties: {
      name: string;
    };
}

function MapStateSelector() {

    const { setState } = useContext(SelectionContext);

  return (
    <ComposableMap projection="geoAlbersUsa">
      {
        <Geographies geography={GEO_URL}>
          {({ geographies }) =>
            geographies.map((geography: GeographyPropsWithName, index: number) => (
              <Geography className="state-style"
                key={index}
                geography={geography}
                stroke="#d3d3d3c1"
                strokeWidth={0.8}
                onClick={() => setState(geography.properties?.name)}
              />
            ))
          }
        </Geographies>
      }
      {US_STATE_ABBREVIATION_DATA.map(({ abbreviation, coordinates }) => {
        return (
          <Marker
            key={abbreviation}
            coordinates={[coordinates[1], coordinates[0]]}
            id={abbreviation}
          >
            <text className="state-text" textAnchor="middle">{abbreviation}</text>
          </Marker>
        );
      })}
    </ComposableMap>
  );
}

export default MapStateSelector;

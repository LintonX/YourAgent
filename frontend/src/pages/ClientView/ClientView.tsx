import React from 'react'
import NavBar from '../../components/NavBar/NavBar'
import LocationSearch from '../../components/LocationSearch/LocationSearch'
import "../../App.css";
import '../ClientView/clientView.css'
import { CLIENT_VIEW_FEATURES_CONTENT, CLIENT_VIEW_NAVBAR_ITEMS } from '../../constants'
import { PLACE_AND_STATE_DATA } from '../../Data/town_state_list'
import ClientFeatureCard from '../../components/ClientFeatureCard/ClientFeatureCard';

function ClientView() {
  return (
    <>
      <NavBar navBarItems={CLIENT_VIEW_NAVBAR_ITEMS}/>
      <div className='client-view-layout'>
        <div className='row client-top-container' id='row client-top-container'>
          <div className='search-header-container'>
            <h1 className='search-header'>
              Find a local <span style={{color:"var(--quaternary-color)"}}>expert</span> in an area below.
            </h1>
          </div>
          <div className='search-container'>
            <LocationSearch suggestions={PLACE_AND_STATE_DATA}/>
          </div>
        </div>
        <div className='row client-bottom-container' id='row client-bottom-container'>
          <div className='client-features-container'>
            <h1 className='client-features-header'>
              How It Works
            </h1>
            <div style={{display:"flex", justifyContent:"space-evenly"}}>
              {CLIENT_VIEW_FEATURES_CONTENT.map((item, index) => (
                <ClientFeatureCard key={index} step={item.step} title={item.title} description={item.description}/>
              ))}
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default ClientView
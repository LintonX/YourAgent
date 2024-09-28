import React from 'react'
import '../../App.css'
import '../NavBar/navBar.css'
import { YOUR_AGENT_SVG } from '../../constants'
import PrimaryButton from '../PrimaryButton/PrimaryButton'

function NavBar() {

    const scrollToSection = (sectionName: string) => {
        console.log("i was clickedddddd")
        const section = document.getElementById(sectionName);
        console.log(section)
        if (section) {
          section.scrollIntoView({ behavior: 'smooth' });
        }
    };

  return (
    <div className='nav-bar-container'>
        <div className='nav-bar'>
            <div className='nav-bar-left'>
                <img id='your-agent-logo' src={YOUR_AGENT_SVG} alt="YourAgent logo" onClick={() => {
                    document.documentElement.scrollTop = 0;
                }} />
                <ul>
                    <li onClick={() => scrollToSection('features-container')}>
                        How It Works
                    </li>
                    <li onClick={() => scrollToSection('pricing-cards-container')}>
                        Pricing
                    </li>
                </ul>
            </div>
            <div className='nav-bar-right'>
                <PrimaryButton 
                    text='Sign in'
                    className="btn secondary"
                    onClick={() => {console.log("clicked")}}
                />
            </div>
        </div>
    </div>
  )
}

export default NavBar
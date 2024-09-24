import React from 'react'
import '../../App.css'
import '../NavBar/navBar.component.css'
import PrimaryButton from '../PrimaryButton/PrimaryButton'
import { useRef } from 'react'

function NavBar() {

    const featuresRef = useRef<HTMLDivElement | null>(null);

    const handleFeatureButtonClick = () => {
        featuresRef.current?.scrollIntoView()
    }

  return (
    <div className='nav-bar-container'>
        <div className='nav-bar'>
            <div>
                <PrimaryButton
                    variant='light'
                    text='Features'
                    className='btn btn-nav-bar1'
                    onClick={handleFeatureButtonClick}
                />
                <PrimaryButton 
                    variant='light'
                    text='Pricing'
                    className='btn btn-nav-bar1'
                    onClick={handleFeatureButtonClick}
                />
            </div>
            <div>
                <PrimaryButton 
                    variant='light'
                    text='Sign in'
                    className='btn btn-nav-bar2'
                    onClick={handleFeatureButtonClick}
                />
            </div>
        </div>
    </div>
  )
}

export default NavBar
import React from 'react'
import '../../App.css'
import './pricingCard.css'

type PricingCardProps = {
    price: string;
    title: string;
    briefDescription: string;
    description: React.ReactNode;
    GetStartedButton: React.ReactNode;
};

function PricingCard({ price, title, briefDescription, description, GetStartedButton }: PricingCardProps) {
  return (
    <div className='pricing-card'>
        <div className='pricing-card-top'>
            <p id='card-title'>
                {title}
            </p>
        </div>
        <div>
            <p id='brief-description'>
                {briefDescription}
            </p>
        </div>
        <div className='pricing-card-middle'>
            <p>
                ${price} <span id='per-month'>/mo</span>
            </p>
        </div>
        <hr id='hr-line'/>
        <div className='pricing-card-bottom'>
            {description}
        </div>
        <div className='button-container'>
            {GetStartedButton}
        </div>
    </div>
  )
}

export default PricingCard
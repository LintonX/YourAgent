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
            <p className='card-header'>
                {title}
            </p>
        </div>
        <div className='brief-description-container'>
            <p className='brief-description'>
                {briefDescription}
            </p>
        </div>
        <div className='pricing-card-middle'>
            <p>
                ${price} <span className='per-month'>/mo</span>
            </p>
        </div>
        <hr className='hr-line'/>
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
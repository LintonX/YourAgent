import React from 'react'
import '../../App.css'
import '../FeatureCard/featureCard.css'
import { FeatureContent } from '../../constants'

interface FeatureCardProps {
    featuresContent: FeatureContent;
}

function FeatureCard(featuresCardProp: FeatureCardProps){
    const featureContent: FeatureContent = featuresCardProp.featuresContent;

    return (
      <div className='feature-card'>
          <div>
            <div className='icon-title-row'>
                <img src={featureContent.icon} alt={featureContent.title} width="26px" height="26px"/>
                <h6 className='feature-title'>{featureContent.title}</h6>
                <hr style={{ opacity: .08 }} />
            </div>
            <p className='feature-detail'>{featureContent.content}</p>
          </div>
      </div>
    );
  };
  
export default FeatureCard
import React from 'react'
import '../../App.css'
import '../PrimaryButton/primaryButton.css'

type PrimaryButtonProps = {
  text: string;
  className: string;
  onClick: (event: React.MouseEvent<HTMLButtonElement>) => void | null;
};

function PrimaryButton({ text, onClick, className }: PrimaryButtonProps) {

  return (
    <button onClick={onClick} className={className}>{text}</button>
  )
}

export default PrimaryButton
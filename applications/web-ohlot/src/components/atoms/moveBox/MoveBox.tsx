import React from 'react';

type MoveBoxPros = {
  text: string;
};

const MoveBox = (props: MoveBoxPros) => {
  return <div>{props.text}</div>;
};

export default MoveBox;

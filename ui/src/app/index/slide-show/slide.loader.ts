import { animate, group, query, style, transition, trigger } from '@angular/animations';

export const slideImagesList = [
  {image: 'assets/images/slide-show/aion1.jpeg', description: 'first image'},
  {image: 'assets/images/slide-show/aion2.jpeg', description: 'second image'},
  {image: 'assets/images/slide-show/aion3.jpeg', description: 'third image'}
];

export const slideAnimation = trigger('slideAnimation', [
  transition(':increment', group([
    query(':enter', [
      style({
        transform: 'translateX(100%)'
      }),
      animate('0.5s ease-out', style('*'))
    ]),
    query(':leave', [
      animate('0.5s ease-out', style({
        transform: 'translateX(-100%)'
      }))
    ])
  ])),
  transition(':decrement', group([
    query(':enter', [
      style({
        transform: 'translateX(-100%)'
      }),
      animate('0.5s ease-out', style('*'))
    ]),
    query(':leave', [
      animate('0.5s ease-out', style({
        transform: 'translateX(100%)'
      }))
    ])
  ]))
]);

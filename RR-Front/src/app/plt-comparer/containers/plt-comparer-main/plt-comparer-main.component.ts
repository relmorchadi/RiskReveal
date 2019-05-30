import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-plt-comparer-main',
  templateUrl: './plt-comparer-main.component.html',
  styleUrls: ['./plt-comparer-main.component.scss']
})
export class PltComparerMainComponent implements OnInit {
  switchValue = false;
  defaultImport;
  colorSwitcher = ['#A96EFE', '#06B8FF', '#F5A623', '#03DAC4', '#E3B8FF', '#0700CF', '#ADFEFA', '#1C607C'];

  cardContainer = [
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
    {
      title: '02PY276 - 2018',
      subtitle: 'P - 4019 - Pricing 2018',
      tableHeader: '[84339] EUET - BE - EUWS AMMA',
      selected: false,
      table: [
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['291,621,790', '291,621,790', '291,621,790', '291,621,790'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
        ['1,00', '1,00', '1,00', '1,00'],
      ],
      result: [
        ['323,849', '323,849', '323,849', '323,849'],
        ['5,806,767', '5,806,767', '5,806,767', '5,806,767'],
        ['17,83', '17,83', '17,83', '17,83']
      ]
    },
  ];


  constructor() { }

  ngOnInit() {
  }

}

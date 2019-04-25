import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit {

  currentPlt = null;
  notChecked = false;
  checked = true;

  plts : any = [
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: false, checked: true},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: false, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: true},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: false, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: true},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: false, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: true},
    {pltId: 1 , pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false}
  ]

  systemTags = [
    {tagid:'1', tagName:'TC', tagColor:'#7bbe31', innerTagContent:'1', innerTagColor:'#a2d16f'},
    {tagid:'2', tagName:'NATC-USM', tagColor:'#7bbe31', innerTagContent:'2', innerTagColor:'#a2d16f'},
    {tagid:'3', tagName:'Post-Inured', tagColor:'#006249', innerTagContent:'9', innerTagColor:'#4d917f'},
    {tagid:'4', tagName:'Pricing', tagColor:'#009575', innerTagContent:'0', innerTagColor:'#4db59e'},
    {tagid:'5', tagName:'Accumulation', tagColor:'#009575', innerTagContent:'2', innerTagColor:'#4db59e'},
    {tagid:'6', tagName:'Default', tagColor:'#06b8ff', innerTagContent:'1', innerTagColor:'#51cdff'},
    {tagid:'7', tagName:'Non-Default', tagColor:'#f5a623', innerTagContent:'0', innerTagColor:'#f8c065'},
  ];
  userTags = [
    {tagid:'1', tagName:'Pricing V1', tagColor:'#893eff', innerTagContent:'1', innerTagColor:'#ac78ff'},
    {tagid:'2', tagName:'Pricing V2', tagColor:'#06b8ff', innerTagContent:'2', innerTagColor:'#51cdff'},
    {tagid:'3', tagName:'Final Princing', tagColor:'#c38fff', innerTagContent:'5', innerTagColor:'#d5b0ff'}
  ];

paths = [
  {id: '1', icon: 'icon-assignment_24px', text: 'P-1686 Ameriprise 2018'},
  {id: '2', icon: 'icon-assignment_24px', text: 'P-1724 Trinity Kemper 2018'},
  {id: '3', icon: 'icon-assignment_24px', text: 'P-8592 TWIA 2018'},
  {id: '4', icon: 'icon-assignment_24px', text: 'P-9035 Post-insured PLTs'},
  {id: '5', icon: 'icon-sitemap', text: 'IP-1135 CXL2 Cascading 2018'}
]

  constructor() { }


  ngOnInit() {
  }

  visible = false;
  size = 'large';

  openDrawer(): void {
    this.visible = true;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  openPltInDrawer(plt) {
    this.plts.forEach(pt => pt.selected = false);
    plt.selected = true;
    this.visible = true;
  }
}
